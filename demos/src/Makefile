srcdir     := .
outdir     := ..
tmpdir     := $(outdir)/tmp
jredir     := ../../JREs
demos.java := $(wildcard $(srcdir)/*/*java)

vpath %.java $(srcdir)
vpath %.jar $(outdir)

.PHONY: all clean

all:


define demos_template

$1.demos.jar := $$(demos.java:$(srcdir)/%.java=$(outdir)/jre$1/%.jar)

$1.clean :
	rm -rf $(outdir)/jre$1

all      : $$($1.demos.jar)
clean    : $1.clean

$(outdir)/jre$1/%.jar : %.java
	@echo
	rm -rf $(tmpdir)
	mkdir -p $(tmpdir)/build
	javac $$< -d $(tmpdir)/build -bootclasspath $(jredir)/jre$1/lib/rt.jar -source $1 -target $1 -g
	jar cvfe $(tmpdir)/tmp.jar $$(basename $$(notdir $$<)) -C $(tmpdir)/build .
	mkdir -p $$(dir $$@)
	mv $(tmpdir)/tmp.jar $$@
	@echo -e "\nCleaning up tmp files..."
	rm -rf $(tmpdir)

endef


JREs := 1.3 1.4 1.5 1.6 1.7
$(foreach JRE,$(JREs),$(eval $(call demos_template,$(JRE))))
