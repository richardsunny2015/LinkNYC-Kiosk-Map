(ns link-nyc-map.fetch-data
  (:require [clj-http.client :as http]
            [tea-time.core :as tt]))

;; TODOS add log info

(def ^:const fifteen-minutes (* 15 2 60))

(defn fetch
  []
  (try
    (->> (http/get "https://data.cityofnewyork.us/resource/n6c5-95xh.json"
                   {:query-params {:$limit 3000}})
         :body
         (spit "resources/json/links.json"))
    (println "Successfully fetched links data")
    (catch Exception e
      (println "Failed to fetch updated data"
               (.getMessage e)))))

(println "Scheduling task for every 15 minutes")

(tt/start!)

(def scheduled-task
  (tt/every! fifteen-minutes
             fifteen-minutes
             (bound-fn []
               (println "Fetching scheduled data")
               (fetch))))

(defn get-handler
  []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body
   (slurp "resources/json/links.json")})