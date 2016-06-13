(ns address-book.rpc
  (:require [sv.rpc.cljs.core :as rpc]
            [address-book.config :as cfg]))

(def config {:path (str (cfg/server) "/rpc")})

(def call (rpc/call-fn config))

(def call-back (rpc/call-back-fn config))
