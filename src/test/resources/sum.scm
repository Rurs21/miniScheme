(begin
  
  (define do-sum (lambda (xs resultat)
    (if (< (count xs) 1.0)
      resultat
      (do-sum (tail xs) (+ resultat (head xs))))))
  
  (define sum (lambda (xs)
    (do-sum xs 0.0)))

  (sum (list 1.0 2.0 3.0 4.0 5.0)))
