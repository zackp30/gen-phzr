(ns gen-phaser.codegen.core
  (:require [cheshire.core :as c]
            [cljfmt.core :as cfmt]
            [clojure.java.io :as io]
            [cuerdas.core :as str]
            [gen-phaser.codegen.constants :as cc]
            [gen-phaser.codegen.extend :as ce]
            [gen-phaser.codegen.function :as cf]
            [gen-phaser.codegen.properties :as cp]
            [gen-phaser.codegen.ns :as cns]
            [gen-phaser.util :as u]))


(defn ^:private build-data
  [json]
  (let [json-data (c/parse-string json true)
        class-col (:classes json-data)]
    (into {} (for [klass class-col] [(:name klass) klass]))))

(defn ^:private fix-bad-doc-in-image-pre-update
  [data]
  (let [image-klass         (get data "Phaser.Image")
        img-functions       (:functions image-klass)
        fixed-img-functions (u/distinct-by img-functions
                                           #(= "preUpdate" (:name %)))]
    (if-not (= (count img-functions) (count fixed-img-functions))
      (assoc data
             "Phaser.Image"
             (assoc image-klass :functions fixed-img-functions))
      data)))

(defn ^:private remove-duplicate-just-pressed-from-gamepad
  [data]
  (let [gamepad-klass      (get data "Phaser.Gamepad")
        gp-functions       (:functions gamepad-klass)
        fixed-gp-functions (u/distinct-by gp-functions
                                          #(= "justPressed" (:name %)))]
    (if-not (= (count gp-functions) (count fixed-gp-functions))
      (assoc data
             "Phaser.Gamepad"
             (assoc gamepad-klass :functions fixed-gp-functions))
      data)))

(defn ^:private fix-bad-doc-in-tween-repeat-all
  [data]
  (let [tween-klass        (get data "Phaser.Tween")
        tween-functions    (:functions tween-klass)
        fixed-tw-functions (map #(if (and (= "repeat" (:name %))
                                          (= 1 (count (:parameters %))))
                                   (assoc % :name "repeatAll")
                                   %)
                                tween-functions)]
    (assoc data
           "Phaser.Tween"
           (assoc tween-klass :functions fixed-tw-functions))))

(defn ^:private fix-bad-doc-in-debug-geom
  [data]
  (let [debug-klass         (get data "Phaser.Utils.Debug")
        debug-functions     (:functions debug-klass)
        fixed-dbg-functions (map #(if (and (= "geom" (:name %))
                                           (= 3 (count (:parameters %))))
                                    (assoc % :name "rectangle")
                                    %)
                                 debug-functions)]
    (assoc data
           "Phaser.Utils.Debug"
           (assoc debug-klass :functions fixed-dbg-functions))))

(defn ^:private fix-bad-create-body-in-p2
  [data]
  (let [p2-klass     (get data "Phaser.Physics.P2")
        p2-functions (:functions p2-klass)
        cb-fn        (first (filter #(= "createBody" (:name %)) p2-functions))
        fixed-params (->> (:parameters cb-fn)
                          (remove #(re-find #"options\." (:name %)))
                          (map #(if (#{"options" "points"} (:name %))
                                  (assoc % :optional true)
                                  %)))
        fixed-cb-fn  (assoc cb-fn :parameters fixed-params)
        fixed-p2-fns (map #(if (= "createBody" (:name %))
                             fixed-cb-fn
                             %)
                          p2-functions)]
    (assoc data
           "Phaser.Physics.P2"
           (assoc p2-klass
                  :functions fixed-p2-fns))))

(defn ^:private fix-bad-create-particle-in-p2
  [data]
  (let [p2-klass     (get data "Phaser.Physics.P2")
        p2-functions (:functions p2-klass)
        cp-fn        (first (filter #(= "createParticle" (:name %))
                                    p2-functions))
        fixed-params (->> (:parameters cp-fn)
                          (remove #(re-find #"options\." (:name %)))
                          (map #(if (#{"options" "points"} (:name %))
                                  (assoc % :optional true)
                                  %)))
        fixed-cp-fn  (assoc cp-fn :parameters fixed-params)
        fixed-p2-fns (map #(if (= "createParticle" (:name %))
                             fixed-cp-fn
                             %)
                          p2-functions)]
    (assoc data
           "Phaser.Physics.P2"
           (assoc p2-klass
                  :functions fixed-p2-fns))))

(defn ^:private fix-bad-add-polygon-in-p2-body
  [data]
  (let [p2b-klass     (get data "Phaser.Physics.P2.Body")
        p2b-functions (:functions p2b-klass)
        ap-fn         (first (filter #(= "addPolygon" (:name %))
                                     p2b-functions))
        fixed-params  (->> (:parameters ap-fn)
                           (remove #(re-find #"options\." (:name %)))
                           (map #(if (#{"options" "points"} (:name %))
                                   (assoc % :optional true)
                                   %)))
        fixed-ap-fn   (assoc ap-fn :parameters fixed-params)
        fixed-p2b-fns (map #(if (= "addPolygon" (:name %))
                              fixed-ap-fn
                              %)
                           p2b-functions)]
    (assoc data
           "Phaser.Physics.P2.Body"
           (assoc p2b-klass
                  :functions fixed-p2b-fns))))

(defn ^:private massage-data
  [data]
  (-> data
      (fix-bad-doc-in-image-pre-update)
      (remove-duplicate-just-pressed-from-gamepad)
      (fix-bad-doc-in-tween-repeat-all)
      (fix-bad-doc-in-debug-geom)
      (fix-bad-create-body-in-p2)
      (fix-bad-create-particle-in-p2)
      (fix-bad-add-polygon-in-p2-body)))

(defn ^:private public-access?
  [f]
  (not (#{"protected" "private"} (:access f))))

(defn ^:private build-form-data
  [klass]
  (let [class-name  (:name klass)
        functions   (filter public-access? (:functions klass))
        ns-form     (cns/gen-ns class-name functions)
        constructor (cf/gen-constructor class-name (:constructor klass))
        constants   (cc/gen-constants class-name
                                      (->> (:members klass)
                                           (filter public-access?)
                                           (filter #(= "constant" (:kind %)))))
        properties  (cp/gen-properties class-name
                                       (->> (:members klass)
                                            (filter public-access?)
                                            (filter #(= "member" (:kind %)))))
        extend-form (ce/gen-extend class-name)
        fn-forms    (map #(cf/gen-function class-name %) functions)]
    {:ns          ns-form
     :constructor constructor
     :constants   constants
     :properties  properties
     :extend      extend-form
     :functions   fn-forms}))

(def ^:private export-whitelist
  #{})

(defn ^:private export-class-name?
  [s]
  (or (re-find #"Phaser\." s)
      (re-find #"PIXI\." s)
      (export-whitelist s)))

(defn ^:private class-keys
  [data]
  (->> (keys data)
       (filter export-class-name?)))

(defn gen-forms
  [json-resource-name]
  (let [data          (build-data (slurp (io/resource json-resource-name)))
        massaged-data (massage-data data)
        export-data   (select-keys massaged-data (class-keys massaged-data))]
    (into {} (for [[class-name klass] export-data]
               [class-name (build-form-data klass)]))))

(defn build-file-text
  [class-name form-data]
  (let [ns-form     (:ns form-data)
        constructor (:constructor form-data)
        constants   (:constants form-data)
        properties  (:properties form-data)
        extend-form (:extend form-data)
        functions   (:functions form-data)]
    (str ns-form
         "\n\n\n"
         constructor
         "\n\n\n"
         (if constants
           (str constants
                "\n\n\n")
           "")
         "\n\n\n"
         (if properties
           (str properties
                "\n\n\n")
           "")
         extend-form
         "\n\n\n"
         (str/join "\n\n" functions))))
