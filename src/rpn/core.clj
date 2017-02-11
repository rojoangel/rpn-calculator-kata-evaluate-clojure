(ns rpn.core
  (:require [clojure.string :as str]))

(defn- operation->tokens [operation]
  (str/split (str/trim operation) #"\s+"))

(defn- tokens->symbols [tokens]
  (map read-string tokens))

(defn- add-number [tree n]
  (reverse (cons n (reverse tree))))

(defn- add-operation [tree op]
  (let [fn (resolve op)]
    (reverse
      (cons
        (cons fn (take-last 2 tree))
        (reverse
          (drop-last 2 tree))))))

(defn- add-symbol [tree symbol]
  (if (number? symbol)
    (add-number tree symbol)
    (add-operation tree symbol)))

(defn- format [tree]
  (str/join " " tree))

(defn calculate [operation]
  (format (map eval (reduce add-symbol nil (tokens->symbols (operation->tokens operation))))))
