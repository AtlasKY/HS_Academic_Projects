####################################################
# Students' Makefile for the CS:APP Architecture Lab
####################################################

sim:
	(cd sim; make)

# Use this rule to "hand in" Part A ("make handin-parta")
handin-parta:
	git add sim/misc/sum.ys
	git add sim/misc/rsum.ys
	git add sim/misc/copy.ys
	git commit -m "handin part a"
	git push

# Use this rule to "handin" Part B ("make handin-partb")
handin-partb:
	git add sim/seq/seq-full.hcl
	git commit -m "handin part b"
	git push

# Use this rule to "handin" Part C ("make handin-partc")
handin-partc:
	git add sim/pipe/pipe-nt.hcl
	git commit -m "handin part c"
	git push

clean:
	rm -f *~ *.o
