(ns pedestal-duct.service
  (:require [integrant.core :as ig]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [ring.util.response :as ring-resp]))

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

;; Defines "/" and "/about" routes with their associated :get handlers.
;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(defn make-common-interceptors
  [deps]
  [(body-params/body-params) http/json-body])

(defn create-routes
  [deps]
  (let [common-interceptors (make-common-interceptors deps)]
    ;; Tabular routes
    #{["/" :get (conj common-interceptors `home-page)]
      ["/about" :get (conj common-interceptors `about-page)]}

    ;; Map-based routes
    ; {"/" {:interceptors [(body-params/body-params) http/html-body]
    ;                   :get home-page
    ;                   "/about" {:get about-page}}}

    ;; Terse/Vector-based routes
    ; [[["/" {:get home-page}
    ;      ^:interceptors [(body-params/body-params) http/html-body]
    ;      ["/about" {:get about-page}]]]]
    ))


(defmethod ig/init-key ::routes
  [_ {:keys [deps]}]
  #(route/expand-routes (create-routes deps)))

(defmethod ig/init-key ::env
  [_ v]
  v)

(defmethod ig/init-key ::service-map
  [_ service-map]
  service-map)
