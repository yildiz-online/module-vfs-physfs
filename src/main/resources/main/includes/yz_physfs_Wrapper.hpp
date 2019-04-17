/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

#ifndef YZ_PHYSFS_WRAPPER_H
#define YZ_PHYSFS_WRAPPER_H

#include <physfs.h>
#include <string>
#include <iostream>
#include <vector>
#include <stdexcept>
#include "yz_physfs_Container.hpp"
#include "yz_physfs_ArchiveTypeInfo.hpp"

namespace yz {

namespace physfs {

class Wrapper {

public:

    Wrapper() {
        if(PHYSFS_isInit() == 0) {
            PHYSFS_init(NULL);
        }
    }

    std::vector<yz::physfs::ArchiveTypeInfo*> getSupportedArchiveType() const {
        const PHYSFS_ArchiveInfo** supported =  PHYSFS_supportedArchiveTypes();
        std::vector<yz::physfs::ArchiveTypeInfo*> list;
        for (; *supported != 0; ++supported) {
            list.push_back(new yz::physfs::ArchiveTypeInfo(*supported));
        }
        return list;
    }

    Container* registerContainer(const std::string& path) const {
        return new Container(path);
    }

    void allowSimLinks(bool allow) {
        PHYSFS_permitSymbolicLinks(allow?1:0);
    }

    void close() {
        if(PHYSFS_isInit() != 0) {
            if (!PHYSFS_deinit()) {
                PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
                throw std::runtime_error(PHYSFS_getErrorByCode(code));
            }
        }
    }

    std::vector<std::string> getSearchPath() const {
        char** locList = PHYSFS_getSearchPath();
        if (locList == 0) {
            PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
            throw std::runtime_error(PHYSFS_getErrorByCode(code));
        }

        std::vector<std::string> list;
        for (char** l = locList; *l != 0; ++l) {
            list.push_back(*l);
        }

        PHYSFS_freeList(locList);
        return list;
    }

    std::string getRealDir(const std::string& file) const {
        std::string dir = PHYSFS_getRealDir(file.c_str());
        if (dir.empty()) {
            throw std::runtime_error("PhysFS::getRealDir: File not found");
        }
        return dir;
    }

    std::vector<std::string> getFileListing(const std::string& dir) const {
        std::vector<std::string> tmpList = enumerateFiles(dir);
        std::vector<std::string> list;
        for (std::vector<std::string>::iterator it = tmpList.begin(); it != tmpList.end(); ++it) {
            if (!isDirectory(*it)) {
                list.push_back(*it);
            }
        }
        return list;
    }

    std::vector<std::string> getDirListing(const std::string& dir) const {
        std::vector<std::string> tmpList = enumerateFiles(dir);
        std::vector<std::string> list;
        for (std::vector<std::string>::iterator it = tmpList.begin(); it != tmpList.end(); ++it) {
            if (isDirectory(*it)) {
                list.push_back(*it);
            }
        }
        return list;
    }

    std::vector<std::string> enumerateFiles(const std::string& dir) const {
        std::vector<std::string> list;
        char** lst = PHYSFS_enumerateFiles(dir.c_str());
        for (char** l = lst; *l != 0; ++l) {
            list.push_back(*l);
        }
        PHYSFS_freeList(lst);
        return list;
    }

    /*std::streampos FileDevice::seek(std::streamoff off, std::ios_base::seekdir way)
                                                            {
                                                              PHYSFS_sint64 pos (off);
                                                              if (way == std::ios_base::cur)
                                                              {
                                                                int cur = this->file->tell();
                                                                pos = cur + off;
                                                              }
                                                              else if (way == std::ios_base::end)
                                                              {
                                                                int end = file->getSize();
                                                                pos = end + off;
                                                              }
                                                              file->seek(pos);

                                                              return std::streampos(pos);
                                                            }*/



    long getLastModTime(const std::string& file) const {
        PHYSFS_Stat stat;
        PHYSFS_stat(file.c_str(), &stat);
        return stat.modtime;
    }
    
    bool exists(const std::string& file) const {
        return PHYSFS_exists(file.c_str()) != 0;    
    }
    
    bool isDirectory(const std::string& file) const {
        return false;
    }

};
}
}

#endif
