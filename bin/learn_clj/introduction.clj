(ns learn-clj.introduction
   (:require [clojure.java.jdbc :as sql]))

(def db {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//10.10.10.97:3306/Movie"
               :user "siva1"
               :password "welcome"})

(defn list-users []
        (sql/with-connection db
          (sql/with-query-results rows
            ["select * from user_details"]
            (println rows))))



(list-users)