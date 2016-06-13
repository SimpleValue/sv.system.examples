(ns address-book.config
  (:require [cljs.reader :as edn]))

(defn config* []
  (edn/read-string (aget js/window "app_config")))

(def config (memoize config*))

(defn server []
  (get (config) :server))
