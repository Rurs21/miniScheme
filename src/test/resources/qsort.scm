(begin

  (define qsort (lambda (xs)
    (if (< (count xs) 2.0)
      xs
      (append (qsort (filter (lambda (x) (< x (head xs))) (tail xs)))
                 (list (head xs))
                 (qsort (filter (lambda (x) (not (< x (head xs)))) (tail xs)))))))

  (qsort (list 5.0 4.0 2.0 3.0 1.0)))
