(ns ql-items-timing-practice.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))



(defn welcome-panel []
  (fn []
    [:div.jumbotron
     [:h1
      "Trainer Info"]
     [:p.lead
      "This trainer will give you 20 questions, each will include random item and pick-up time.
       Your goal is to type a correct time of next spawn of the same item quickly.
       At the end, trainer will give you statistics of correct/incorrect answers."
      [:p
       [:a.btn.btn-lg.btn-success
        {:class    "mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
         :href     "#"
         :role     "button"
         :on-click #(do
                     (.preventDefault %)
                     (re-frame/dispatch [:init-training])
                     (re-frame/dispatch [:add-new-question])
                     (re-frame/dispatch [:set-active-panel :trainer]))}
        "Start practice"]]]]))


(defn answer-input []
  (let [current-answer (re-frame/subscribe [:current-answer])]
    (reagent/create-class
      {:component-function
       (fn []
         [:input.answer {:type      :text
                         :value     (:input @current-answer)
                         :on-change #(re-frame/dispatch [:on-current-answer-change (-> % .-target .-value)])}])

       :component-did-mount
       #(.focus (reagent/dom-node %))})))



(defn trainer-panel []
  (let [current-question (re-frame/subscribe [:current-question])
        current-question-state (re-frame/subscribe [:current-question-state])]
    (reagent/create-class
      {:component-function
       (fn []
         [:div
          {:on-key-down #(when (= 13 (.-keyCode %))
                          (if (= :submitted (:state @current-question-state))
                            (re-frame/dispatch [:add-new-question])
                            (re-frame/dispatch [:submit-answer])))}

          [:div.row
           [:div.col-xs-10.col-xs-offset-1
            [:div.col-xs-4
             (let [img-filename (str (name (:item @current-question)) ".png")]
               [:img.thumbnail
                {:src   (str "img/icons/" img-filename)
                 :width "128px"}])]

            [:div.col-xs-8
             [:div.row
              [:div.col-xs-12
               [:div.col-xs-6
                [:div.statistics.text-center
                 [:div.value.given-value (:time @current-question)]
                 [:div.label "Pick Up"]]]

               [:div.col-xs-6
                [:div.statistics.text-center
                 [:div.value [answer-input]]
                 [:div.label "Next Spawn"]]]]]

             (when (= :submitted (:state @current-question-state))
               [:div.row {:style {:margin-top "20px"}}
                (if (= (:correct @current-question-state) (:input @current-question-state))
                  [:div.alert.alert-success "Correct!"]
                  [:div.alert.alert-danger "Wrong! Correct spawn time is "
                   [:span.statistics
                    [:span.value {:style {:color "#a94442"}}
                     (:correct @current-question-state)]]])])]]]

          (when-not (= :submitted (:state @current-question-state))
            [:div.row
             [:div.col-xs-6.col-xs-offset-3
              [:div.text-center
               [:h2 "Answer in " (inc (:ticks-left @current-question-state)) " seconds..."]]]])])})))


(defn page-not-found []
  [:div 404])



(defmulti panels identity)
(defmethod panels :welcome [] [welcome-panel])
(defmethod panels :trainer [] [trainer-panel])
(defmethod panels :default [] [:div])




(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [:div.container
       [:div.header.clearfix
        [:h3.text-muted
         "Quake Live Items Timing Trainer"]]

       [:hr]

       (panels @active-panel)

       ;[:footer.footer
       ; [:p
       ;  "© 2015 Company, Inc."]]
       ])))
