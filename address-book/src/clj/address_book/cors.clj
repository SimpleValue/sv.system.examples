(ns address-book.cors
  (:require [ring.middleware.cors :as c]))

(defn wrap-access-control-max-age [handler max-age]
  (fn [request]
    (let [response (handler request)]
      (if (and (= (:request-method request) :options)
               (get-in response [:headers "Access-Control-Allow-Origin"]))
        (assoc-in response [:headers "Access-Control-Max-Age"] max-age)
        response))))

(defn wrap-cors [handler]
  (-> handler
      (c/wrap-cors
       :access-control-allow-origin [#"http://localhost:3449"
                                     #"http://localhost:8080"]
       :access-control-allow-methods [:post])
      (wrap-access-control-max-age (* 60 10))))
