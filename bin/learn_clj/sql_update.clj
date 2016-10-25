(ns learn-clj.sql_update
   (:require [clojure.java.jdbc :as sql]))

(def db {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//10.10.10.97:3306/clojure_test"
               :user "siva1"
               :password "welcome"})
               
(defn update-blog
  "This method updates a blog entry"
  [id attribute-map]
  (sql/update-values
   :blogs
   ["id=?" id]
   attribute-map))
   
(sql/with-connection db 
  (sql/transaction
    (update-blog 1 {:title "Awesome Title"})))
    
 (sql/with-connection db 
   (sql/with-query-results rs ["select * from blogs"] 
     (dorun (map #(println (:title :body %)) rs))))



