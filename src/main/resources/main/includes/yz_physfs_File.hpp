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

#ifndef YZ_PHYSFS_FILE_H
#define YZ_PHYSFS_FILE_H

#include <physfs.h>
#include <string>
#include <stdexcept>
#include <iostream>

namespace yz {

namespace physfs {

class File {

public:

    File(const std::string& path) {
        std::cout << "open file" << std::endl;
        std::cout << "opening:" << path.c_str() << std::endl;
        this->file = PHYSFS_openRead(path.c_str());
        std::cout << "opened" << std::endl;
        if(!this->file) {
            std::cout << "error" << std::endl;
            PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
            throw std::runtime_error(PHYSFS_getErrorByCode(code));
        }
    }

    PHYSFS_sint64 getSize() const {
        return PHYSFS_fileLength(this->file);
    }

    void close() {
        if(PHYSFS_close(this->file) == 0) {
            PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
            throw std::runtime_error(PHYSFS_getErrorByCode(code));
        }
    }

    bool isEof() const {
        return PHYSFS_eof(this->file) != 0;
    }

    int readBytes(void* data, int count) const {
        int read = PHYSFS_readBytes(this->file, data, count);
        if (read < count && !this->isEof()){
            PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
            throw std::runtime_error(PHYSFS_getErrorByCode(code));
        }
        return read;
    }

    int seek(int position) const {
        int result = PHYSFS_seek(this->file, position);
        if(result == 0) {
            PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
            throw std::runtime_error(PHYSFS_getErrorByCode(code));
        }
        return result;
    }

    int tell() const {
        int result = PHYSFS_tell(this->file);
        if(result == -1) {
            PHYSFS_ErrorCode code = PHYSFS_getLastErrorCode();
            throw std::runtime_error(PHYSFS_getErrorByCode(code));
        }
        return result;
    }


private:

    PHYSFS_file* file;

};
}
}

#endif
