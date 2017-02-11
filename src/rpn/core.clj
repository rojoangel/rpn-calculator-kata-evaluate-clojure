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

(defn- walk [tree]
  (map eval tree))

(defn- format [tree]
  (str/join " " tree))

(defn calculate [operation]
  (let [tokens (operation->tokens operation)]
    (format (walk (reduce add-symbol nil (tokens->symbols tokens))))))
