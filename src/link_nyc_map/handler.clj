(ns link-nyc-map.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [link-nyc-map.fetch-data :as fetch-data]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (route/files "/" {:root "resources/public"})
  (GET "/links" [] (fetch-data/get-handler))
  (route/not-found "Not Found"))

(defn- create-app
  []
  (println "Fetching initial data")
  (fetch-data/fetch)
  (println "Finished fetching data. Starting app.")
  (wrap-defaults app-routes site-defaults))

(def app
  (create-app))
