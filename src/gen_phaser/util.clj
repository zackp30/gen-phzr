(ns gen-phaser.util
  (:require [camel-snake-kebab.core :as csk]
            [cuerdas.core :as str]))

(defn ^:private kebab-touchup
  [s]
  (-> s
      (str/replace #"web\-gl" "webgl")
      (str/replace #"p\-2js" "p2-js")
      (str/replace #"p\-2" "p2")
      (str/replace #"palette\-c\-64" "palette-c64")
      (str/replace #"box\-2\-d" "box-2d")))

(defn name->kebab
  [s]
  (-> s
      (csk/->kebab-case-string)
      (str/lower)
      (kebab-touchup)))

(defn ^:private snake-touchup
  [s]
  (-> s
      (str/replace #"web\_gl" "webgl")
      (str/replace #"p\_2" "p2")))

(defn name->snake
  [s]
  (-> s
      (csk/->snake_case_string)
      (str/lower)
      (snake-touchup)))

(defn instance-arg-name
  [class-name]
  (name->kebab (last (str/split class-name #"\."))))

(defn distinct-by
  [coll id-fn]
  (let [step (fn step [xs seen]
               (lazy-seq
                ((fn [[f :as xs] seen]
                   (when-let [s (seq xs)]
                     (let [k (id-fn f)]
                       (if (and k (contains? seen k))
                         (recur (rest s) seen)
                         (cons f (step (rest s) (conj seen k)))))))
                 xs seen)))]
    (step coll #{})))

(def ^:private export-whitelist
  #{})

(def ^:private export-blacklist
  #{"Phaser.Physics.Ninja"
    "Phaser.Physics.Ninja.AABB"
    "Phaser.Physics.Ninja.Body"
    "Phaser.Physics.Ninja.Circle"
    "Phaser.Physics.Ninja.Tile"
    "Phaser.Physics.Box2D"
    "Phaser.Plugin.AStar"
    "Phaser.Plugin.AStar.AStarNode"
    "Phaser.Plugin.AStar.AStarPath"
    "Phaser.Plugin.Juicy"
    "Phaser.Plugin.Juicy.ScreenFlash"
    "Phaser.Plugin.Juicy.Trail"
    "Phaser.Plugin.TilemapWalker"})

(defn ^:private export-class-name?
  [s]
  (and (or (re-find #"Phaser\." s)
           (re-find #"PIXI\." s)
           (export-whitelist s))
       (not (export-blacklist s))))

(defn export-class-names
  [data]
  (->> (keys data)
       (filter export-class-name?)))
