## Generate data for the testing.

Pull the image

> docker pull scalytics/tpch

Generate 1 Gb of data in this directory (windows style)

>  docker run -it -v "$(PWD):/data" scalytics/tpch:latest -vf -s 1

This generates the following files that serves as the base data with 1 Gb in the dataset:
* customer.tbl
* lineitem.tbl
* nation.tbl
* orders.tbl
* part.tbl
* partsupp.tbl
* region.tbl
* supplier.tbl

To get even more data, alter the `-s ` that is the Scale Factor 

Build update sets.

> docker run -it -v "$(PWD):/data" scalytics/tpch:latest -v -U 1 -s 1

* delete.1
* lineitem.tbl.u1
* orders.tbl.u1

