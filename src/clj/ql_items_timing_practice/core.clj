(ns ql-items-timing-practice.core)


(def data {:questions '({:item :ya, :time 7} {:item :ya, :time 25} {:item :mh, :time 11}),
           :answers   '({:correct 32, :input 32} {:correct 50, :input 50} {:correct 46, :input 46}), :current-question {:state :submitted, :correct 32, :input 32}})


(->> (interleave (:questions data) (:answers data))
     (partition 2)
     (map #(apply merge %)))

