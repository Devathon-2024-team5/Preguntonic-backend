.PHONY: githooks

githooks:
	cp githooks/pre-commit `git rev-parse --git-dir`/hooks/pre-commit
	chmod +x `git rev-parse --git-dir`/hooks/pre-commit