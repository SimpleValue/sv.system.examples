(ns address-book.rpc
  (:require [address-book.cors :as cors]))

(defn components []
  [{:binds [:ring :handlers :rpc/handler]
    :start [(fn [handler]
              (-> handler
                  (cors/wrap-cors)))
            [:rpc :ring-handler]]}])
