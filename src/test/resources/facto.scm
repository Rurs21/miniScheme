(begin

  (define facto (lambda (n)
    (if (< n 2.0)
           1.0
           (* n (facto (- n 1.0))))))

  (facto 5.0))
