(ns ql-items-timing-practice.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))



(re-frame/register-sub
  :active-panel
  (fn [db]
    (reaction (:active-panel @db))))
