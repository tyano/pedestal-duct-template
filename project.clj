(defproject pedestal-duct "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [duct/core "0.8.1"]
                 [io.pedestal/pedestal.service "0.6.3"]

                 ;; Remove this line and uncomment one of the next lines to
                 ;; use Immutant or Tomcat instead of Jetty:
                 [io.pedestal/pedestal.jetty "0.6.3"]
                 ;; [io.pedestal/pedestal.immutant "0.6.3"]
                 ;; [io.pedestal/pedestal.tomcat "0.6.3"]

                 [com.taoensso/timbre "6.5.0"]
                 [com.fzakaria/slf4j-timbre "0.4.1"]
                 [org.slf4j/jul-to-slf4j "2.0.13"]
                 [org.slf4j/jcl-over-slf4j "2.0.13"]
                 [org.slf4j/log4j-over-slf4j "2.0.13"]]
  :plugins [[duct/lein-duct "0.12.3"]]
  :main ^:skip-aot pedestal-duct.main
  :resource-paths ["resources"]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.2"]
                                   [hawk "0.2.11"]
                                   [eftest "0.5.9"]
                                   [kerodon "0.9.1"]
                                   [io.pedestal/pedestal.service-tools "0.6.0"]]}})
