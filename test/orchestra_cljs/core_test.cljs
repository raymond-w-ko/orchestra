(ns orchestra-cljs.core-test
  (:require ;#?@(:clj [[clojure.test :refer :all]
            ;          [clojure.spec.alpha :as s]
            ;          [orchestra.spec.test :refer :all]]

            ;  :cljs [[cljs.test :refer-macros [deftest testing is use-fixtures]]
            ;         [cljs.spec :as s]
            ;         [orchestra.spec.test :refer-macros [instrument unstrument
            ;                                             with-instrument-disabled]]])
            [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [cljs.spec.alpha :as s]
            [orchestra-cljs.spec.test :as orchestra
             :refer-macros [with-instrument-disabled instrument unstrument]]))

(defn instrument-fixture [f]
  (unstrument)
  (instrument)
  (f))
(use-fixtures :each instrument-fixture)

(defn args'
  [meow]
  true)
(s/fdef args'
        :args (s/cat :meow string?))

(deftest args
  (testing "Positive"
    (is (args' "meow")))
  (testing "Negative"
    (is (thrown? :default ;#?(:clj RuntimeException :cljs :default)
                 (args' 42)))))

;(defn ret'
;  [meow]
;  meow)
;(s/fdef ret'
;        :ret integer?)
;
;(deftest ret
;  (testing "Positive"
;    (is (ret' 42)))
;  (testing "Negative"
;    (is (thrown? :default ;#?(:clj RuntimeException :cljs :default)
;                 (ret' true)))))
;
;(defn func'
;  [meow]
;  (Math/abs meow))
;(s/fdef func'
;        :args (s/cat :meow number?)
;        :fn #(= (:ret %) (-> % :args :meow)))
;
;(deftest func
;  (testing "Positive"
;    (is (func' 42)))
;  (testing "Negative"
;    (is (thrown? :default ;#?(:clj RuntimeException :cljs :default)
;                 (func' -42)))))
;
;(defn full'
;  [meow]
;  (Math/abs meow))
;(s/fdef full'
;        :args (s/cat :meow number?)
;        :fn #(let [meow (-> % :args :meow)
;                   ret (:ret %)]
;               (or (= ret meow)
;                   (and (< meow 0)
;                        (= (- ret) meow))))
;        :ret number?)
;
;(deftest full
;  (testing "Positive"
;    (is (full' 0))
;    (is (full' -10))))
;
;(defn empty-spec'
;  [meow]
;  (Math/abs meow))
;(s/fdef empty')
;
;(deftest empty-spec
;  (testing "Positive"
;    (is (empty-spec' 0))))
;
;(defn func-no-args-spec
;  [meow]
;  (Math/abs meow))
;(s/fdef func-no-args-spec
;        :fn #(= (:ret %) (-> % :args :meow)))
;
;(deftest func-negative
;  (testing "Negative"
;    (is (thrown? :default ;#?(:clj RuntimeException :cljs :default)
;                 (func-no-args-spec -42)))))
;
;(deftest disabled
;  (testing "Positive"
;    (with-instrument-disabled
;      (is (func-no-args-spec -42)))))