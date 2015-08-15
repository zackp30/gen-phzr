(ns phzr.impl.accessors.text)

(def text-get-properties
  {:align "align"
   :alive "alive"
   :alpha "alpha"
   :anchor "anchor"
   :angle "angle"
   :animations "animations"
   :auto-cull "autoCull"
   :auto-round "autoRound"
   :blend-mode "blendMode"
   :body "body"
   :bottom "bottom"
   :bounds-align-h "boundsAlignH"
   :bounds-align-v "boundsAlignV"
   :cache-as-bitmap "cacheAsBitmap"
   :camera-offset "cameraOffset"
   :canvas "canvas"
   :check-world-bounds "checkWorldBounds"
   :children "children"
   :colors "colors"
   :context "context"
   :crop-rect "cropRect"
   :css-font "cssFont"
   :damage "damage"
   :debug "debug"
   :delta-x "deltaX"
   :delta-y "deltaY"
   :delta-z "deltaZ"
   :destroy-phase "destroyPhase"
   :events "events"
   :exists "exists"
   :fill "fill"
   :filter-area "filterArea"
   :filters "filters"
   :fixed-to-camera "fixedToCamera"
   :font "font"
   :font-size "fontSize"
   :font-style "fontStyle"
   :font-variant "fontVariant"
   :font-weight "fontWeight"
   :frame "frame"
   :frame-name "frameName"
   :fresh "fresh"
   :game "game"
   :heal "heal"
   :health "health"
   :height "height"
   :hit-area "hitArea"
   :in-camera "inCamera"
   :in-world "inWorld"
   :input "input"
   :input-enabled "inputEnabled"
   :key "key"
   :left "left"
   :lifespan "lifespan"
   :line-spacing "lineSpacing"
   :mask "mask"
   :max-health "maxHealth"
   :name "name"
   :offset-x "offsetX"
   :offset-y "offsetY"
   :out-of-bounds-kill "outOfBoundsKill"
   :padding "padding"
   :parent "parent"
   :pending-destroy "pendingDestroy"
   :physics-type "physicsType"
   :pivot "pivot"
   :position "position"
   :previous-position "previousPosition"
   :previous-rotation "previousRotation"
   :render-order-id "renderOrderID"
   :renderable "renderable"
   :resolution "resolution"
   :right "right"
   :rotation "rotation"
   :scale "scale"
   :scale-max "scaleMax"
   :scale-min "scaleMin"
   :shader "shader"
   :shadow-blur "shadowBlur"
   :shadow-color "shadowColor"
   :shadow-fill "shadowFill"
   :shadow-offset-x "shadowOffsetX"
   :shadow-offset-y "shadowOffsetY"
   :shadow-stroke "shadowStroke"
   :smoothed "smoothed"
   :stage "stage"
   :stroke "stroke"
   :stroke-colors "strokeColors"
   :stroke-thickness "strokeThickness"
   :tabs "tabs"
   :text "text"
   :text-bounds "textBounds"
   :texture "texture"
   :tint "tint"
   :tinted-texture "tintedTexture"
   :top "top"
   :transform-callback "transformCallback"
   :transform-callback-context "transformCallbackContext"
   :type "type"
   :visible "visible"
   :width "width"
   :word-wrap "wordWrap"
   :word-wrap-width "wordWrapWidth"
   :world "world"
   :world-alpha "worldAlpha"
   :world-position "worldPosition"
   :world-rotation "worldRotation"
   :world-scale "worldScale"
   :world-visible "worldVisible"
   :x "x"
   :y "y"
   :z "z"})

(def text-set-properties
  {:align "align"
   :alive "alive"
   :alpha "alpha"
   :anchor "anchor"
   :angle "angle"
   :animations "animations"
   :auto-cull "autoCull"
   :auto-round "autoRound"
   :blend-mode "blendMode"
   :body "body"
   :bounds-align-h "boundsAlignH"
   :bounds-align-v "boundsAlignV"
   :cache-as-bitmap "cacheAsBitmap"
   :camera-offset "cameraOffset"
   :canvas "canvas"
   :check-world-bounds "checkWorldBounds"
   :colors "colors"
   :context "context"
   :crop-rect "cropRect"
   :css-font "cssFont"
   :damage "damage"
   :debug "debug"
   :events "events"
   :exists "exists"
   :fill "fill"
   :filter-area "filterArea"
   :filters "filters"
   :fixed-to-camera "fixedToCamera"
   :font "font"
   :font-size "fontSize"
   :font-style "fontStyle"
   :font-variant "fontVariant"
   :font-weight "fontWeight"
   :frame "frame"
   :frame-name "frameName"
   :game "game"
   :heal "heal"
   :health "health"
   :height "height"
   :hit-area "hitArea"
   :input "input"
   :input-enabled "inputEnabled"
   :key "key"
   :lifespan "lifespan"
   :line-spacing "lineSpacing"
   :mask "mask"
   :max-health "maxHealth"
   :name "name"
   :out-of-bounds-kill "outOfBoundsKill"
   :padding "padding"
   :pending-destroy "pendingDestroy"
   :pivot "pivot"
   :position "position"
   :renderable "renderable"
   :resolution "resolution"
   :rotation "rotation"
   :scale "scale"
   :scale-max "scaleMax"
   :scale-min "scaleMin"
   :shader "shader"
   :shadow-blur "shadowBlur"
   :shadow-color "shadowColor"
   :shadow-fill "shadowFill"
   :shadow-offset-x "shadowOffsetX"
   :shadow-offset-y "shadowOffsetY"
   :shadow-stroke "shadowStroke"
   :smoothed "smoothed"
   :stroke "stroke"
   :stroke-colors "strokeColors"
   :stroke-thickness "strokeThickness"
   :tabs "tabs"
   :text "text"
   :texture "texture"
   :tint "tint"
   :tinted-texture "tintedTexture"
   :transform-callback "transformCallback"
   :transform-callback-context "transformCallbackContext"
   :type "type"
   :visible "visible"
   :width "width"
   :word-wrap "wordWrap"
   :word-wrap-width "wordWrapWidth"
   :world "world"
   :world-visible "worldVisible"
   :x "x"
   :y "y"
   :z "z"})