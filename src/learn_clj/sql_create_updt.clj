(ns learn-clj.sql_create_updt
   (:require [clojure.java.jdbc :as j]))

(def db-spec {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//10.10.10.97:3306/clojure_test"
               :user "siva1"
               :password "welcome"})


(j/delete! db-spec :fruit ["grade > ?" 25.0])
;(j/execute! db-spec ["DELETE FROM fruit WHERE name = ?" "Orange"])

(try
(j/drop-table-ddl :fruit {:entities clojure.string/upper-case})
 (catch Exception _))


(j/create-table-ddl :fruit
                    [[:name "varchar(32)" :primary :key]
                     [:appearance "varchar(32)"]
                     [:cost :int]
                     [:grade :real]]
                    {:table-spec "ENGINE=InnoDB"
                     :entities clojure.string/upper-case})


;(j/insert! db-spec :fruit
;  {:name "Apple" :appearance "rosy" :cost 24})

(j/insert-multi!
  db-spec
  :fruit
  [:name :appearance :cost :grade]
  [["Apple" "red" 59 87]
   ["Banana" "yellow" 29 92.2]
   ["Peach" "fuzzy" 139 90.0]
   ["Orange" "juicy" 89 88.6]])

(doseq [row (j/query db-spec ["SELECT * FROM fruit"])] 
          (println row)) ;;before update

(doseq [row (j/query db-spec ["DESCRIBE fruit"])] 
          (println row)) 

(j/update! db-spec :fruit {:name "Apple" :appearance "blue" :cost 999} ["name = ?" "Apple"])

(doseq [row (j/query db-spec ["SELECT * FROM fruit"])] 
          (println row)) ;;after update
