(ns learn-clj.sql_create
   (:require [clojure.java.jdbc :as sql]))

(def db {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//10.10.10.97:3306/clojure_test"
               :user "siva1"
               :password "welcome"})
(defn drop-blogs
  "Drop the blogs table"
  []
  (try
   (sql/drop-table :blogs)
   (catch Exception _)))
   
 (sql/with-connection
   db
   (sql/transaction
     (drop-blogs)))
     
(defn create-blogs
  "Create a table to store blog entries"
  []
  (sql/create-table
   :blogs
   [:id :integer "PRIMARY KEY" "AUTO_INCREMENT"]
   [:title "varchar(255)"]
   [:body :text]))

(sql/with-connection
   db
   (sql/transaction
     (create-blogs)))

(defn insert-blog-entry
  "Insert data into the table"
  [title,body]
  (sql/insert-values
   :blogs
   [:title :body]
   [title body]))
   
 (sql/with-connection
   db
   (sql/transaction
    (insert-blog-entry "Hello World" "Life is awesome in the lisp world.") 
     (insert-blog-entry "Big data" "How about Big data analytics.") 
      (insert-blog-entry "Hadoop" "What about Hadoop.")   
      (insert-blog-entry "Junk" "JUnk tetssttststts.")   
      ))
 
   (defn delete-blog
  "Deletes a blog entry given the id"
  [id]
  (sql/with-connection db
   (sql/delete-rows :blogs ["id=?" id])))
 
 (sql/with-connection db 
  (sql/transaction
    (delete-blog 4 )))
 
 (sql/with-connection db 
   (sql/with-query-results rs ["select * from blogs"] 
     (dorun (map #(println (:title :body %)) rs))))