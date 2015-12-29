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
  :start-new-training
  (fn [db _]
    (let [question (engine/new-random-item)
          answer {:correct (engine/next-time (:item question) (:time question))
                  :input   ""}]
      (assoc db
        :current-training {:questions (list question)
                           :answers   (list answer)}))))



(re-frame/register-handler
  :on-current-answer-change
  (fn [db [_ new-value]]
    (update-in db [:current-training :answers]
               (fn [answers]
                 (cons (assoc (first answers) :input new-value) (rest answers))))))

