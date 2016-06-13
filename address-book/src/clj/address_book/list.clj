(ns address-book.list
  (:require [datomic.api :as d]))

(defn q-person-names [db]
  (d/q
   '[:find [(pull ?e [:person/first-name :person/last-name]) ...]
     :where [?e :person/first-name]]
   db))

(defn get-persons [con]
  (q-person-names (d/db con)))

(defn components []
  [{:binds [:rpc :fns :get-persons]
    :start [partial
            get-persons
            [:datomic :con]]}])
