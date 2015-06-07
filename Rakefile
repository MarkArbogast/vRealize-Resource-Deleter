JAR_NAME = 'vrealize-resource-deleter'
VERSION = '1.0.0'

namespace :build do
  desc "Compile all sources"
  task :compile do
    compile_code("lib", "src", "out")
  end

  desc "Create jar file from sources"
  task :jar => ["build:compile"] do 
    create_jar('out')
  end

  def compile_code(library_dir, source_dir, target_dir)
    puts
    puts "*** COMPILING ***"
    puts

    libraries = Dir["#{library_dir}/**/*.jar"]
    sources = Dir["#{source_dir}/**/*.java"]
    class_path = libraries.join(":")
    source_files = sources.join(" ")

    javac_command = %|javac -cp "#{class_path}" -d "#{target_dir}" -sourcepath "#{source_dir}" -Xlint:unchecked -source "1.7" -target "1.7" #{source_files}|

    puts "javac_command: <#{javac_command}>"

    unless system(javac_command)
      abort("Compile failed")
    end
  end

  def create_jar(compile_dir)
    puts
    puts "*** CREATING JAR ***"
    puts

    jar_include_files = Dir["#{compile_dir}/**/*.class"].map do |path|
      path.gsub("out" + "/", "").gsub("$", "\\$")
    end.join(" ")

    jar_file = "#{JAR_NAME}-#{VERSION}.jar"

    cd "out" do
      jar_command = %|jar cfm #{jar_file} ../Manifest.txt "com"|
      puts "jar_command: <#{jar_command}>"
      `#{jar_command}`
      `mv #{jar_file} ..`
    end
    
    puts
    puts "*** SUCCESS ***"
    puts "jar: #{jar_file}"
  end

end
