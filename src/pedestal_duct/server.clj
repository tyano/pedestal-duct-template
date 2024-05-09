(ns pedestal-duct.server
  (:require [integrant.core :as ig]
            [io.pedestal.http :as server]))

(defmethod ig/init-key ::server
  [_ {{:keys [env] :as service-map} :service-map}]
  {:pre [service-map env]}
  (println "\nCreating your server... (" env ")")
  (let [service-map   (if (= env :dev)
                        (-> service-map
                            server/default-interceptors
                            server/dev-interceptors)
                        service-map)
        server-config (server/create-server service-map)]
    (server/start server-config)))

(defmethod ig/halt-key! ::server
  [_ server]
  (println "\nStopping your server...")
  (server/stop server))

(defmethod ig/init-key ::host
  [_ host]
  host)

;; If you package the service up as a WAR,
;; some form of the following function sections is required (for io.pedestal.servlet.ClojureVarServlet).

;;(defonce servlet  (atom nil))
;;
;;(defn servlet-init
;;  [_ config]
;;  ;; Initialize your app here.
;;  (reset! servlet  (server/servlet-init service/service nil)))
;;
;;(defn servlet-service
;;  [_ request response]
;;  (server/servlet-service @servlet request response))
;;
;;(defn servlet-destroy
;;  [_]
;;  (server/servlet-destroy @servlet)
;;  (reset! servlet nil))