(begin

  (define do-fmap (lambda (f xs ys)
    (if (< (count xs) 1.0)
      ys
      (do-fmap f (tail xs) (append ys (list (f (head xs))))))))

  (define fmap (lambda (f xs)
    (do-fmap f xs (list))))

  (define au-carre (lambda (n)
    (* n n)))

  (fmap au-carre (list 1.0 2.0 3.0 4.0 5.0)))
