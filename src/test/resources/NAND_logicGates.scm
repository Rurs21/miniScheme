(begin
  (define NAND (lambda (A B)
    (not (and A B))
  ))
  (define OR (lambda (A B)
    (NAND (NAND A A) (NAND B B))
  ))
  (define NOR (lambda (A B)
    (not (OR A B))
  ))

  (define XOR (lambda (A B)
    (NAND
      (NAND A (NAND A B))
      (NAND B (NAND A B))
    )
  ))

  (define XNOR (lambda (A B)
    (NAND
        (NAND (NAND A B) (NAND B B))
        (NAND A B)
    )
  ))

  (define MUX (lambda (A B S)
    (NAND
        (NAND A (NAND S S))
        (NAND B S))
  ))

  (define DEMUX (lambda (I S)
     (define A (NAND I (NAND S S)))
     (define A (NAND A A))
     (define B (NAND I S))
     (define B (NAND B B))
     (list A B)
  ))

  (and (NAND #f #t) (OR #f #t) (NOR #f #f) (XOR #t #f) (XNOR #t #t) (MUX #t #f #f))
)
;;https://en.wikipedia.org/wiki/NAND_logic
