(ns dev
  (:require [datomic.api :as d]))

(defn db []
  (d/db (:con (:datomic sv.system.main/system))))

(defn restart []
  (sv.system.main/restart))
