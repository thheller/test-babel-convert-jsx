(ns demo.app
  (:require
    ["@babel/core" :as babel]
    ["@babel/plugin-transform-react-jsx" :as jsx]
    ["buffer" :as Buffer]
    [shadow.dom :as dom]))

;; fix some bad babel file that uses Buffer global instead of require("buffer")
(js/goog.exportSymbol "Buffer" Buffer)

(def test-str
  "function render(model){\n  return <div style={{textAlign: \"center\"}}>\n           <h1>{model.active_states[0].name}</h1>\n         </div>;\n}")

(defn ^:export init []
  (let [dom-input (dom/by-id "input")
        dom-output (dom/by-id "output")
        dom-button (dom/by-id "convert")]

    (dom/set-value dom-input test-str)

    (dom/on dom-button :click
      (fn [e]
        (let [result (babel/transform (dom/get-value dom-input) #js {:plugins #js [jsx]})]
          (js/console.log "result" result)
          (dom/set-value dom-output (.-code result)))
        ))))