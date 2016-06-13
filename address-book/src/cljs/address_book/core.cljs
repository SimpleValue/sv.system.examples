(ns address-book.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]
            [address-book.rpc :as rpc]
            [cljs.core.async :as a :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce app-state (atom {}))

(defn get-persons []
  (rpc/call :get-persons))

;; -------------------------
;; Views

(defn page []
  [:div [:h2 "Address Book"]
   [:ul
    (map-indexed
     (fn [idx person]
       [:li
        {:key idx}
        (str (:person/first-name person)
             " " (:person/last-name person))])
     (sort-by :person/first-name (:persons @app-state)))]
   [:a
    {:href "#"
     :onClick (fn [e]
                (go
                  (let [persons (<! (get-persons))]
                    (swap! app-state assoc :persons persons))))}
    "load persons"]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
