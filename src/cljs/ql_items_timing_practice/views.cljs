(ns ql-items-timing-practice.views
  (:require [re-frame.core :as re-frame]))



(defn welcome-panel []
  (fn []
    [:div {:class "welcome-card-wide mdl-card mdl-shadow--2dp"}
     [:div {:class "mdl-card__title"}
      [:h2 {:class "mdl-card__title-text"} "Welcome"]]
     [:div {:class "mdl-card__supporting-text"}
      "This trainer will give you 20 questions, each will include random item and pick-up time.
      Your goal is to type a correct time of next spawn of the same item quickly.
      At the end, trainer will give you statistics of correct/incorrect answers."]

     [:div {:class "mdl-card__actions mdl-card--border"}
      [:a {:class    "mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
           :href     "#"
           :on-click #(do
                       (.preventDefault %)
                       (re-frame/dispatch [:set-active-panel :trainer]))}
       "Start"]]]))



(defn page-not-found []
  [:div 404])



(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [:div {:class "mdl-layout mdl-js-layout"}
       [:header {:class "mdl-layout__header"}
        [:div {:class "mdl-layout__header-row"}
         [:span {:class "mdl-layout-title"} "Quake Live Timing Trainer"]
         [:div {:class "mdl-layout-spacer"}]]]

       [:main {:class "mdl-layout__content"}
        [:div {:class "page-content"}


         (condp = @active-panel
           :welcome [welcome-panel]

           [page-not-found])

         ]]])))
