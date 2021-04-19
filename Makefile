.PHONY: build-docker
build-docker:
	./build_scripts/build.sh

	echo build-docker done

.PHONY: run
run:
	docker build -t atm-cash-dispenser . && docker run -it -p9081:9081 atm-cash-dispenser

	echo run done

