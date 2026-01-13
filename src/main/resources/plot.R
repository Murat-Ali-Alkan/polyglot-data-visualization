library(lattice)

data <- numeric(100)

function(dataHolder) {
  tmp <- tempfile(fileext = ".svg")

  svg(file = tmp)

  data <<- c(data[2:100], dataHolder$value)

  df <- data.frame(
    time = -99:0,
    value = data
  )

  p <- xyplot(
    value ~ time,
    data = df,
    main = "Csv Numbers Plot",
    ylab = "Col-6 Data",
    type = c("l", "g"),
    col.line = "darkorange"
  )

  print(p)
  dev.off()

  paste(readLines(tmp), collapse = "\n")
}
