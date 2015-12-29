(ns ql-items-timing-practice.handlers
  (:require [re-frame.core :as re-frame]
            [ql-items-timing-practice.engine :as engine]
            [ql-items-timing-practice.db :as db]))



(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))



(re-frame/register-handler
  :set-active-panel
  (fn [db [_ panel]]
    (assoc db :active-panel panel)))



(re-frame/register-handler
  :init-training
  (fn [db _]
    (assoc db
      :current-training {:questions (list)
                         :answers   (list)})))


(re-frame/register-handler
  :add-new-question
  (fn [db _]
    (let [question (engine/new-random-item)
          answer {:correct (engine/next-time (:item question) (:time question))
                  :input   ""}]

      (-> db
          (update-in [:current-training :questions] conj question)
          (update-in [:current-training :answers] conj answer)
          (assoc-in [:current-training :current-question] {:state :in-progress})))))



(re-frame/register-handler
  :on-current-answer-change
  (fn [db [_ new-value]]
    (update-in db [:current-training :answers]
               (fn [answers]
                 (cons (assoc (first answers) :input (int new-value)) (rest answers))))))



(re-frame/register-handler
  :submit-answer
  (fn [db _]
    (let [answers (get-in db [:current-training :answers])]
      (assoc-in db [:current-training :current-question]
                (merge {:state :submitted} (first answers))))))
