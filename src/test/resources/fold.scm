(begin

  (define fold (lambda (a r l)
    (if (< (count l) 1.0)
      a
      (fold (r a (head l)) r (tail l)))))

  (define fmap (lambda (f l)
    (fold (list) (lambda (a x) (append a (list (f x)))) l)))

  (define compose (lambda (f g)
    (lambda (n)
      (f (g n)))))

  (define sum (lambda (l)
    (fold 0.0 + l)))

  (define add1 (lambda (n)
    (+ n 1.0)))

  (define mul3 (lambda (n)
    (* n 3.0)))

  (define mul3add1 (compose add1 mul3))

  (sum (fmap mul3add1 (list 1.0 2.0 3.0 4.0 5.0))))
