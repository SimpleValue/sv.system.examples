(ns address-book.datomic
  (:require [sv.system.datomic.core :as c]
            [address-book.sample-data :as s]))

(defn basic-data []
  (map
   (fn [[first-name last-name]]
     {:person/first-name first-name
      :person/last-name last-name})
   s/persons))

(def config
  {:dev true
   :datomic {:uri "datomic:mem://address-book"
             :schema [{:db/ident :person/first-name
                       :db/valueType :db.type/string
                       :db/cardinality :db.cardinality/one
                       :db/doc "First name of a person."
                       :db.install/_attribute :db.part/db}
                      {:db/ident :person/last-name
                       :db/valueType :db.type/string
                       :db/cardinality :db.cardinality/one
                       :db/doc "Last name of a person."
                       :db.install/_attribute :db.part/db}]
             :basic-data (basic-data)}})

(defn components [config]
  [(c/connection config)
   (c/schema config)
   (c/basic-data config)])
