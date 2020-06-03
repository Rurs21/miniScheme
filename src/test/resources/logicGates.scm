(begin
  (define NAND (lambda (leftValue rightValue)
    (not (and leftValue rightValue))
  ))
  (define OR (lambda (leftValue rightValue)
    (NAND (NAND leftValue leftValue) (NAND rightValue rightValue))
  ))
  (define NOR (lambda (leftValue rightValue)
    (not (OR leftValue rightValue))
  ))

  (define XOR (lambda (leftValue rightValue)
    (NAND
      (NAND leftValue (NAND leftValue rightValue))
      (NAND rightValue (NAND leftValue rightValue))
    )
  ))

  (and (NAND #f #t) (OR #f #t) (NOR #f #f) (XOR #t #f))
)
