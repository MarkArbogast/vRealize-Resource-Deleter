require 'rest-client'
require 'io/console'
require 'net/http'

connection = nil
while(!(connection = get_connection()))

def get_connection
  print "vRealize Hostname: "
  hostname = gets.chomp

  print "vRealize Username: "
  username = gets.chomp

  print "vRealize Password: "
  password = STDIN.noecho(&:gets).chomp

  puts

  r = Request.new({:user => username, :password => password})
end


