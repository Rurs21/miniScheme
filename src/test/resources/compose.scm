(begin

  (define add1 (lambda (n)
    (+ n 1.0)))

  (define mul3 (lambda (n)
    (* n 3.0)))

  (define compose (lambda (f g)
    (lambda (n)
      (f (g n)))))

  (define mul3add1 (compose add1 mul3))
  (mul3add1 10.0)

  (define flip (lambda (f)
    (lambda (a b)
      (f b a))))

  (define sequence (flip compose))

  (define add1mul3 (sequence add1 mul3))
  (add1mul3 10.0))
