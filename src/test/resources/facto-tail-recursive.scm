(begin

  (define do-facto (lambda (n r)
    (if (< n 2.0)
           r
           (do-facto (- n 1.0) (* n r)))))

  (define facto (lambda (n)
    (do-facto n 1.0)))

  (facto 5.0))
