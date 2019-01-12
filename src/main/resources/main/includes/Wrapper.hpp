/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
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
#include <vector>
#include <stdexcept>
#include "Container.hpp"
#include "ArchiveTypeInfo.hpp"

namespace yz {

namespace physfs {

class Wrapper {

public:

    Wrapper() {
    std::cout << "Initializing physfs." << std::endl;
        if(!PHYSFS_isInit) {
            if (!PHYSFS_init(NULL)) {
            std::cout << "Physfs initialized." << std::endl;
                  throw std::runtime_error(PHYSFS_getLastError());
            } else {
                std::cout << "Physfs initialization failure." << std::endl;
            }
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

    Container* registerContainer(const std::string& path) const{
        return new Container(path);
    }

    void close() {
        if(PHYSFS_isInit) {
            if (!PHYSFS_deinit()) {
                throw std::runtime_error(PHYSFS_getLastError());
            }
        }
    }
};
}
}

#endif
