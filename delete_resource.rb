require 'io/console'
require 'net/http'

print "vRealize Hostname: "
hostname = gets.chomp

print "vRealize Username: "
username = gets.chomp

print "vRealize Password: "
password = STDIN.noecho(&:gets).chomp

puts


