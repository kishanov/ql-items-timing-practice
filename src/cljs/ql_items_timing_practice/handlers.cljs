(ns ql-items-timing-practice.handlers
  (:require [re-frame.core :as re-frame]
            [ql-items-timing-practice.db :as db]))



(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))



(re-frame/register-handler
  :set-active-panel
  (fn [db [_ panel]]
    (assoc db :active-panel panel)))
