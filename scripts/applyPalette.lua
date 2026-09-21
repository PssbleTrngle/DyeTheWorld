local palette = app.open(app.params["palette"]).cels[1].image

replacements = {}
for i=0, palette.width do
   replacements[i] = {
      from = palette:getPixel(i, 0),
      to = palette:getPixel(i, 1)
   }
end

local sprite = Sprite{ fromFile=app.params["from"], oneFrame=true }

for i, replacement in pairs(replacements) do
   app.command.ReplaceColor {
      ui = false,
      from=replacement.from,
      to=replacement.to
   }
end

sprite:saveCopyAs(app.params["to"])