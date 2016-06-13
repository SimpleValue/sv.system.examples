(ns address-book.ring
  (:require [ring.middleware.file :as f]))

(def resources
  (-> (fn [request]
        nil)
      (f/wrap-file "resources/public")))

(defn components []
  [{:binds [:ring :handler]
    :start [identity [:ring :handlers-dispatcher]]}
   {:binds [:ring :handlers ::resources]
    :start [identity #'resources]}])
