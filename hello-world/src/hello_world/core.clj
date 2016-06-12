(ns hello-world.core
  (:require [sv.system.core :as sys]
            [sv.system.main :refer :all]
            [sv.system.httpkit.core :as h]))

(def config
  {:httpkit {:opts {:port 3000}}})

(defn hello-handler [request]
  {:status 200
   :body "Hello World"
   :content-type "text/plain"})

(defn components []
  [(h/httpkit-server config)
   {:binds [:ring :handler]
    :start [identity hello-handler]}])

(set-components
 #'components)
