(ns address-book.system
  (:require [sv.system.core :as sys]
            [sv.system.main :refer :all]
            [sv.system.httpkit.core :as h]
            [sv.system.ring.handlers :as handlers]
            [sv.system.rpc.core :as rpc]
            [sv.system.rpc.plain :as plain]
            [address-book.datomic :as d]
            [address-book.list :as l]
            [address-book.rpc :as r]
            [address-book.ring :as ring]))

(def config
  {:httpkit {:opts {:port 8080}}})

(defn components []
  (concat
   (sys/config-components
    config
    [h/httpkit-server
     handlers/handlers
     rpc/rpc
     plain/plain-get-rpc-fn])
   (d/components d/config)
   (l/components)
   (r/components)
   (ring/components)))

(set-components
 #'components)
